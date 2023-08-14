import React from 'react'
import classes from './CompanyProfileNavigation.module.css';

function CompanyProfileNavigation(props) {
    return (
        <div className={classes.navButtons}>
            <button className={`${classes.button} ${props.activeTab === 'about' ? classes.buttonActive : null}`} onClick={() => props.setactiveTab('about')}>
                About Company
            </button>
            <button className={`${classes.button} ${props.activeTab === 'comments' ? classes.buttonActive : null}`} onClick={() => props.setactiveTab('comments')}>
                Comments
            </button>
            <button className={`${classes.button} ${props.activeTab === 'salary' ? classes.buttonActive : null}`} onClick={() => props.setactiveTab('salary')}>
                Salary and Benefits
            </button>
            <button className={`${classes.button} ${props.activeTab === 'interview' ? classes.buttonActive : null}`} onClick={() => props.setactiveTab('interview')}>
                Interview
            </button>
            <button className={`${classes.button} ${props.activeTab === 'jobs' ? classes.buttonActive : null}`} onClick={() => props.setactiveTab('jobs')}>
                Jobs
            </button>
        </div>
    )
}

export default CompanyProfileNavigation