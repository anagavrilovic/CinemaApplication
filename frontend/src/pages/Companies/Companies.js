import classes from "./Companies.module.css";
import Caption from "../../components/Caption/Caption";
import CompanySearch from "../../components/Company/CompanySearch/CompanySearch";
import CompanyFilter from "../../components/Company/CompanyFilter/CompanyFilter";
import AllCompanies from "../../components/Company/AllCompanies/AllCompanies";
import React, { useEffect, useState } from 'react';
import { getAllCompanies } from "../../api/CompanyApi";

function Companies() {

    const [companies, setCompanies] = useState([]);

    useEffect(() => {
        async function getAll() {
            const results = await getAllCompanies();
            setCompanies(results);
        }

        getAll();
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Companies" />
                <CompanySearch placeholder="Company name, city or key word" />
                <CompanyFilter />
            </div>

            <div className={classes.content}>
                <div className={classes.contentHeader}>
                    <p>{companies.length} Companies</p>

                    <div className={classes.sort}>
                        <p>Sort:</p>
                        <select name="Sort" defaultValue="" className={classes.select}>
                            <option value="impressionsAsc" >Number of Impressions Ascending</option>
                            <option value="impressionsDesc" >Number of Impressions Descending</option>
                        </select>
                    </div>
                </div>

                <AllCompanies companies={companies}/>
            </div>
        </div>
    );
}

export default Companies;