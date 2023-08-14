import React, {useEffect, useState} from 'react'
import { addInterviewInfo, getPositionsByCompany } from '../../../../../api/CompanyApi';
import InteractiveStarRate from '../../../../StarRate/InteractiveStarRate/InteractiveStarRate';
import classes from './AddInterview.module.css'
import { axiosInstance } from '../../../../../api/AxiosInstance';
import { useSelector } from 'react-redux';

const AddInterview = ({ company, toggleAddInterview, reload }) => {

    const [interview, setInterview] = useState({positionId: 1, userId: 1, offerStatus: 0});
    const [positions, setPositions] = useState([]);
    const [error, setError] = useState('');
    const user = useSelector((state) => state.user.value);

    function ratingChanged(value) {
        setInterview(prev => ({ ...prev, mark: parseFloat(value) }));
    }

    useEffect(() => {
        async function getPositions(){
            const results = await getPositionsByCompany(company.id);
            setPositions(results);
        }

        getPositions();

    }, [company]);

    useEffect(() => {
        const config = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${user.accessToken}`
            }
        }
        axiosInstance.get(`/auth/whoami`, config)
            .then((response) => {
                setInterview(prev => ({ ...prev, userId: response.data.id }));
            })
    }, [user])

    function onInterviewChange(e) {
        setError("");
        const element = e.target.id;
        const value = e.target.value;

        switch (element) {
            case "interviewCaption":
                setInterview(prev => ({ ...prev, caption: value }));
                break;
            case "interviewPosition":
                setInterview(prev => ({ ...prev, positionId: parseInt(value) }));
                break;
            case "technicalInterview":
                setInterview(prev => ({ ...prev, technicalInterviewImpression: value }));
                break;
            case "hrInterview":
                setInterview(prev => ({ ...prev, hrInterviewImpression: value }));
                break;
            case "interviewOfferStatus":
                setInterview(prev => ({ ...prev, offerStatus: parseInt(value) }));
                break;
            default:
                console.log("default")
        }

    }

    function validateInput(){
        if(!interview.caption || !interview.hrInterviewImpression || !interview.technicalInterviewImpression || !interview.mark){
            setError("All fields are required.");
            return false;
        }
        return true;
    }

    function onSaveInterviewInformation(e) {
        e.preventDefault();
        
        if(!validateInput()) return;

        addInterviewInfo(interview).then(() => {
            reload();
            toggleAddInterview();
        })
        .catch((e) => {
            setError(e.response.data.message);
        })
    }

    return (
        <div className={classes.fallback} onClick={toggleAddInterview}>
            <div className={classes.modal} onClick={e => e.stopPropagation()}>
                <form>
                    <div className={classes.header}>
                        <span> Add interview information </span>
                        <span className={classes.close} onClick={toggleAddInterview}>&times;</span>

                    </div>

                    <div className={classes.body}>
                        <div className={classes.fieldTitle}>
                            <span> Caption </span>
                        </div>
                        <input type="text" className={classes.input} id="interviewCaption" onChange={onInterviewChange} required></input>

                        <div className={classes.fieldTitle}>
                            <span> Interviewed at </span>
                        </div>
                        <select className={classes.select} id="interviewPosition" onChange={onInterviewChange}>
                            {positions.map((pos) => <option key={pos.id} value={pos.id} className={classes.selectOption} >{pos.title} ({pos.seniority})</option>)}
                        </select>

                        <div className={classes.fieldTitle}>
                            <span> Technical interview impression </span>
                        </div>
                        <textarea className={classes.inputArea} id="technicalInterview" onChange={onInterviewChange} required></textarea>

                        <div className={classes.fieldTitle}>
                            <span> HR interview impression </span>
                        </div>
                        <textarea className={classes.inputArea} id="hrInterview" onChange={onInterviewChange} required></textarea>

                        <div className={classes.fieldTitle}>
                            <span> Offer status </span>
                        </div>
                        <select className={classes.select} id="interviewOfferStatus" onChange={onInterviewChange} >
                            <option className={classes.selectOption} value="0"> Did not get offer </option>
                            <option className={classes.selectOption} value="1"> Rejected offer </option>
                            <option className={classes.selectOption} value="2"> Accepted offer </option>
                        </select>

                        <div className={classes.fieldTitle}>
                            <span className={classes.ratingTitle}> Overall rating </span>
                            <InteractiveStarRate ratingChanged={ratingChanged} />
                        </div>
                        
                        <div className={classes.error}> {error} </div>

                    </div>

                    <div className={classes.footer}>
                        <input type="submit" className={classes.button} value="Save" onClick={onSaveInterviewInformation}/>
                    </div>
                </form>
            </div>

        </div>
    )
}

export default AddInterview