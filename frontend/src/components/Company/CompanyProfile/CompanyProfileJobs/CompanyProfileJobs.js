import React, { useEffect, useState } from 'react';
import AllJobs from '../../../Jobs/AllJobs/AllJobs';

import { axiosInstance } from "../../../../api/AxiosInstance"

function CompanyProfileJobs(props) {

    const [jobs, setJobs] = useState();

    useEffect(() => {
        axiosInstance.get(`/job-offer/all/${props.companyEmail}`)
            .then((response) => {
                setJobs(response.data);
            })
    });

    return (
        <AllJobs jobs={jobs ? jobs : []} />
    )
}

export default CompanyProfileJobs