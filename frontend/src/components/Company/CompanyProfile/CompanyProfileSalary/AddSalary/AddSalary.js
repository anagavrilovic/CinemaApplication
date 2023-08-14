import React, { useEffect, useState } from 'react'
import { addSalaryInfo, getPositionsByCompany } from '../../../../../api/CompanyApi'
import classes from './AddSalary.module.css'
import { axiosInstance } from '../../../../../api/AxiosInstance'
import { useSelector } from 'react-redux'

const AddSalary = ({ toggleAddSalary, company, reload }) => {
    const [salary, setSalary] = useState({positionId: 1, userId: 1});
    const [positions, setPositions] = useState([]);
    const [error, setError] = useState('');
    const user = useSelector((state) => state.user.value);

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
                setSalary(prev => ({ ...prev, userId: response.data.id }));
            })
    }, [user])


    function onPositionSelected(e) {
        setSalary(prev => ({ ...prev, positionId: parseInt(e.target.value) }));
    }

    function onAmountChange(e){
        setError("");
        setSalary(prev => ({ ...prev, amountInEur: parseFloat(e.target.value) }));
    }

    function onSaveSalaryInformation(e){
        e.preventDefault();

        if(salary.amountInEur <= 0 || !salary.amountInEur) {
            setError("Must be greater than 0.");
            return;
        }

        addSalaryInfo(salary).then(() => {
            reload();
            toggleAddSalary();
        })
        .catch((e) => {
            setError(e.response.data.message);
        });

    }

    return (
        <div className={classes.fallback} onClick={toggleAddSalary}>
            <div className={classes.modal} onClick={e => e.stopPropagation()}>
                <form>
                    <div className={classes.header}>
                        <span> Add salary information </span>
                        <span className={classes.close} onClick={toggleAddSalary}>&times;</span>

                    </div>
                    <div className={classes.body}>
                        <div className={classes.fieldTitle}>
                            <span> Available positions </span>
                        </div>
                        <select className={classes.select} onChange={onPositionSelected}>
                            {positions.map((pos) => <option key={pos.id} value={pos.id} className={classes.selectOption} >{pos.title} ({pos.seniority})</option>)}
                        </select>

                        <div className={classes.fieldTitle}>
                            <span> Amount in EUR </span>
                        </div>
                        <input type="number" className={classes.input} id="salaryAmount" onChange={onAmountChange} required></input>
                        <div className={classes.error}> {error} </div>
                    </div>

                    <div className={classes.footer}>
                        <input type="submit" className={classes.button} value="Save" onClick={onSaveSalaryInformation}/>
                    </div>
                </form>
            </div>

        </div>
    )
}

export default AddSalary