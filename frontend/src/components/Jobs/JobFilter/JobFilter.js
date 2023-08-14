import React from 'react';
import classes from "./JobFilter.module.css";

function JobFilter() {
    return (
        <div className={classes.component}>
            <div className={classes.filter}>
                <select name="City" defaultValue="" className={classes.select}>
                    <option value="" disabled>City</option>
                </select>

                <select name="Industry" defaultValue="" className={classes.select}>
                    <option value="" disabled>Industry</option>
                </select>

                <select name="Seniority" defaultValue="" className={classes.select}>
                    <option value="" disabled>Seniority</option>
                    <option value="Internship" >Internship</option>
                    <option value="Junior" >Junior</option>
                    <option value="Medior" >Medior</option>
                    <option value="Senior" >Senior</option>
                </select>

                <select name="Pubished" defaultValue="" className={classes.select}>
                    <option value="" disabled>Pubished</option>
                    <option value="today" >Today</option>
                    <option value="3" >3 days ago</option>
                    <option value="7" >7 days ago</option>
                    <option value="earlier" >Earlier</option>
                </select>
            </div>
        </div>
    )
}

export default JobFilter