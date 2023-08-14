import React from 'react';
import classes from './Company.module.css';
import Synechron from '../../../images/synechron.png';
import { useNavigate } from 'react-router-dom';
import StarRate from '../../StarRate/StarRate';

function Company({company}) {
    const navigate = useNavigate();

    function handleViewCompany() {
        navigate(`/company/${company.company.id}/${company.company.name}`, {state: {company: company.company, companyRate: company.companyRate }});
    }

    return (
        <div className={classes.component}>
            <div className={classes.companyName}>
                <img src={Synechron} alt={company.company.name} />
                <h3>{company.company.name}</h3>
            </div>
            <div className={classes.impressions}>
                <div className={classes.rate}>
                    <div className={classes.stars}>
                        <StarRate rate={company.companyRate} />
                    </div>
                    <p className={classes.textBold}>{company.companyRate}</p>
                </div>
                <p className={classes.textBold}>{company.numberOfComments} impressions</p>
            </div>
            <div className={classes.about}>
                <div className={classes.shortAbout}>
                    <div className={classes.shortAboutLabels}>
                        <p>Website:</p>
                        <p>Employees:</p>
                        <p>Industry:</p>
                    </div>
                    <div className={classes.shortAboutValues}>
                        <p className={classes.textBold}>{company.company.website}</p>
                        <p className={classes.textBold}>{company.company.size}</p>
                        <p className={classes.textBold}>{company.company.industry}</p>
                    </div>
                </div>
                <div className={classes.description}>{company.company.description}</div>
            </div>
            <div className={classes.buttonDetails}>
                <button className={classes.button} onClick={handleViewCompany}>View Company</button>
            </div>
        </div>
    )
}

export default Company