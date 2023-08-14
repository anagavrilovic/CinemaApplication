import React, {useEffect, useState} from 'react'
import { addComment } from '../../../../../api/CompanyApi';
import InteractiveStarRate from '../../../../StarRate/InteractiveStarRate/InteractiveStarRate'
import classes from "./AddComment.module.css"
import { axiosInstance } from '../../../../../api/AxiosInstance';
import { useSelector } from 'react-redux';

const AddComment = ({ toggleAddComment, company, reload }) => {
    const [comment, setComment] = useState({ mark: 0, currentlyWorking: false, companyId: company.id, userId: 1 });
    const [error, setError] = useState('');
    const user = useSelector((state) => state.user.value);


    function ratingChanged(value) {
        setComment(prev => ({ ...prev, mark: parseFloat(value) }));
    }

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
                setComment(prev => ({ ...prev, userId: response.data.id }));
            })
    }, [user])

    function onCommentChange(e) {
        const element = e.target.id;
        const value = e.target.value;

        setError("");

        switch (element) {
            case "commentCaption":
                setComment(prev => ({ ...prev, caption: value }));
                break;
            case "commentIsCurrentlyWorking":
                setComment(prev => ({ ...prev, currentlyWorking: e.target.checked }));
                break;
            case "commentText":
                setComment(prev => ({ ...prev, text: value }));
                break;
            default:
                console.log("default")
        }

    }

    function onCommentSave(e){
        e.preventDefault();

        if(!comment.caption || !comment.text || comment.mark === 0.0){
            setError("All fields are required.");
            return;
        }


        addComment(comment).then(() => {
            reload();
            toggleAddComment();
        })
        .catch((e) => {
            setError(e.response.data.message);
        });
    }

    return (
        <div className={classes.fallback} onClick={toggleAddComment}>
            <div className={classes.modal} onClick={e => e.stopPropagation()}>
                <form>
                    <div className={classes.header}>
                        <span> Add comment </span>
                        <span className={classes.close} onClick={toggleAddComment}>&times;</span>

                    </div>
                    <div className={classes.body}>
                        <div className={classes.fieldTitle}>
                            <span> Caption </span>
                        </div>
                        <input type="text" className={classes.input} id="commentCaption" onChange={onCommentChange}></input>

                        <div className={classes.fieldTitle}>
                            <span> Text </span>
                        </div>
                        <textarea className={classes.inputArea} id="commentText" onChange={onCommentChange}></textarea>

                        <div className={classes.fieldTitle}>
                            <span className={classes.ratingTitle}> Leave a rating </span>
                            <InteractiveStarRate ratingChanged={ratingChanged} />
                        </div>
                        

                        <div className={classes.row}>
                            <input type="checkbox" className={classes.checkbox} id="commentIsCurrentlyWorking" onChange={onCommentChange}></input>
                            <label> I am currently working at this role </label>
                        </div>
                        <div className={classes.error}> {error} </div>
                    </div>

                    <div className={classes.footer}>
                        <input type="submit" className={classes.button} value="Save" onClick={onCommentSave}/>
                    </div>
                </form>
            </div>

        </div>
    )
}

export default AddComment