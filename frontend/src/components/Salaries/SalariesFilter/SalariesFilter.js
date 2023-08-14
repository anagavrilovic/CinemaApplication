import React, { useState } from 'react';
import classes from "./SalariesFilter.module.css";

function SalariesFilter() {
    const [canSearch, setCanSearch] = useState([false, false, false]);

    return (
        <div className={classes.component}>
            <div className={classes.filter}>
                <select name="Position" defaultValue="" className={classes.select} onChange={() => setCanSearch([true, canSearch[1], canSearch[2]])}>
                    <option value="" disabled>Work Position</option>
                    <option value="Something" >Something</option>
                </select>

                <select name="Seniority" defaultValue="" className={classes.select} onChange={() => setCanSearch([canSearch[0], true, canSearch[2]])}>
                    <option value="" disabled>Seniority</option>
                    <option value="Internship" >Internship</option>
                    <option value="Junior" >Junior</option>
                    <option value="Medior" >Medior</option>
                    <option value="Senior" >Senior</option>
                </select>

                <select name="Form" defaultValue="" className={classes.select} onChange={() => setCanSearch([canSearch[0], canSearch[1], true])}>
                    <option value="" disabled>Form of Employment</option>
                    <option value="full" >Full time</option>
                    <option value="part" >Part time</option>
                    <option value="freelance" >Freelance</option>
                    <option value="internship" >Internship</option>
                </select>

                <button className={`${classes.button} 
                    ${canSearch[0] === true && canSearch[1] === true && canSearch[2] === true ? classes.buttonEnabled : classes.buttonDisabled}`}>
                        Search
                </button>
            </div>
        </div>
    )
}

export default SalariesFilter