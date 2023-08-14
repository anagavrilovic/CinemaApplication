import React from 'react';
import classes from './Salary.module.css';

function Salary({salary}) {
    function formatEmployment(emp){
        emp = emp.charAt(0).toUpperCase() + emp.slice(1);
        emp = emp.replace("_", " ");
        return emp;
    }

    return (
        <div className={classes.component}>
            <div className={classes.content}>
                <div className={classes.info}>
                    <div className={classes.infoPart}>
                        <p className={classes.label}>Work position</p>
                        <p className={classes.value}>{salary.title} ({salary.seniority})</p>
                    </div>
                    <div className={classes.infoPart}>
                        <p className={classes.label}>Form of Employment</p>
                        <p className={classes.value}>{formatEmployment(salary.formOfEmployment)}</p>
                    </div>
                    <div className={classes.infoPart}>
                        <p className={classes.label}>Neto Sallary</p>
                        <p className={`${classes.value} ${classes.salaryValue}`}>{salary.avgSalary} EUR</p>
                    </div>
                    <div className={classes.infoPart}>
                        <p className={classes.label}></p>
                        <p className={classes.value}>({salary.numberOfSalaries} {salary.numberOfSalaries === 1 ? 'salary' : 'salaries'})</p>
                    </div>
                </div>
                <div className={classes.salaries}>
                    <div className={classes.salariesLabel}>
                        <p className={classes.label}>Min</p>
                        <div className={classes.avgSalary}>
                            <p>{salary.avgSalary}</p>
                            <div className={classes.circle}></div>
                        </div>
                        <p className={classes.label}>Max</p>
                    </div>
                    <div className={classes.salariesValue}>
                        <p>{salary.minSalary}</p>
                        <div className={classes.scale}></div>
                        <p>{salary.maxSalary}</p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Salary;