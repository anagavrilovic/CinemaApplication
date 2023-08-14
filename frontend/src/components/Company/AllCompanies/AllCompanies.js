import React from 'react';
import Company from '../Company/Company';
import classes from './AllCompanies.module.css';

function AllCompanies({ companies }) {

    return (
        <div className={classes.component} > {
            companies.map((company) => {
                return <Company key={company.company.name} company={company} />
            })
        }
        </div>
    )
}

export default AllCompanies