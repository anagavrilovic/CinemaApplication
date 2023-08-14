import React from 'react';
import classes from './CompanyFilter.module.css';

function CompanyFilter() {
    return (
        <div className={classes.component}>
            <div className={classes.filter}>
                <select name="Location" defaultValue="" className={classes.select}>
                    <option value="" disabled>Location</option>
                </select>

                <select name="Industry" defaultValue="" className={classes.select}>
                    <option value="" disabled>Industry</option>
                </select>

                <select name="Employees" defaultValue="" className={classes.select}>
                    <option value="" disabled>Number of Employees</option>
                    <option value="20" >&lt;20</option>
                    <option value="50" >20-50</option>
                    <option value="100" >51-100</option>
                    <option value="250" >101-250</option>
                    <option value="500" >251-500</option>
                    <option value="1000" >500-1000</option>
                    <option value="1000+" >&gt;1000</option>
                </select>

                <select name="Rate" defaultValue="" className={classes.select}>
                    <option value="" disabled>Rate</option>
                    <option value="2" >1-2</option>
                    <option value="3" >2-3</option>
                    <option value="4" >3-4</option>
                    <option value="5" >4-5</option>
                </select>
            </div>
        </div>
    )
}

export default CompanyFilter