import React from 'react';
import classes from './RegistrationRequest.module.css';
import Synechron from '../../../images/synechron.png';
import { approveRequest, rejectRequest } from '../../../api/CompanyRegistrationApi'

function RegistrationRequest({company, reload}) {

    function approveRequestHandler(event) {
        event.preventDefault();
        approveRequest(company.id).then(() => {
            reload();
        });
    }

    function rejectRequestHandler(event) {
        event.preventDefault();
        rejectRequest(company.id).then(() => {
            reload();
        });
    }

    return (
        <div className={classes.component}>
            <div className={classes.companyName}>
                <img src={Synechron} alt={company.name} />
                <h3>{company.name}</h3>
            </div>
            <div className={classes.about}>
                <div className={classes.shortAbout}>
                    <div className={classes.shortAboutLabels}>
                        <p>Website:</p>
                        <p>Employees:</p>
                        <p>Industry:</p>
                    </div>
                    <div className={classes.shortAboutValues}>
                        <p className={classes.textBold}>{company.website}</p>
                        <p className={classes.textBold}>{company.size}</p>
                        <p className={classes.textBold}>{company.industry}</p>
                    </div>
                </div>
                <div className={classes.description}>{company.description}</div>
                <div className={classes.buttonDetails}>
                    <button className={classes.buttonReject}  onClick={rejectRequestHandler}  >Reject </button>
                    <button className={classes.buttonApprove} onClick={approveRequestHandler}>Approve</button>
                </div>
            </div>
          
        </div>
    )
}

export default RegistrationRequest