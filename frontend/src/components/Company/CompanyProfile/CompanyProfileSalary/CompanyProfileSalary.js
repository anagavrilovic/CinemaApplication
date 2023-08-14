import React from 'react';
import Salary from '../../../Salaries/Salary/Salary';

function CompanyProfileSalary({companyId, salaries}) {

    return (
        <div>
            {
                salaries.map((s) => {
                    return <Salary key={s.title.toString() + s.formOfEmployment.toString() + s.seniority.toString()} salary={s}/> 
                })
            }
        </div>
    )
}

export default CompanyProfileSalary