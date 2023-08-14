import React from 'react';
import classes from './Job.module.css';
import Synechron from '../../../images/synechron.png';
import JobOfferService from '../../../services/JobOfferService';
import { useSelector } from 'react-redux';
import { axiosInstance } from "../../../api/AxiosInstance"

function Job({ job, publishToDislinkt }) {

    const user = useSelector((state) => state.user.value);

    function publish(){
        const config = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${user.accessToken}`
            }
        }
        axiosInstance.get(`/auth/whoami`, config)
            .then((response) => {
                console.log(response.data.email)
                JobOfferService.sendOffer(job.id,response.data.email).then(resp=>{
                    console.log(resp.data)
                    const dto ={
                        token : resp.data.token,
                        jobOffer: {
                            employment_type: 0,
                            jobDescription: resp.data.jobOffer.jobDescription,
                            position: resp.data.jobOffer.position,
                            prerequisites: resp.data.jobOffer.prerequisites,
                            company : {
                                id : String("723b0cc3a34d25d8567f9f82"),
                                company_name: resp.data.jobOffer.company.name,
                                username: resp.data.jobOffer.company.name,
                                email: resp.data.jobOffer.company.email,
                                phone_number: resp.data.jobOffer.company.phone_number,
                                description: resp.data.jobOffer.company.description,
                                location: resp.data.jobOffer.company.location,
                                website: resp.data.jobOffer.company.website,
                                company_size: resp.data.jobOffer.company.size,
                                industry: resp.data.jobOffer.company.industry,
                                IsActive: true
                            }
                        }
                    }
                     console.log(dto)
                    JobOfferService.sendToDislinkt(dto).then(resp1=>{
                        console.log(resp1.data)
                    })
                }).catch(err=>{
                    alert("No dislinkt token!")
                })
            })
    }

    return (
        <div className={classes.component}>
            <div className={classes.imageWrapper}>
                <img src={Synechron} alt={job.company.name} className={classes.image} />
            </div>
            <div className={classes.jobInfo}>
                <h3 className={classes.jobTitle}>{job.position}</h3>
                <p className={classes.mainText}>{job.company.name}</p>
                <p className={classes.mainText}>
                    {job.company.cities}
                </p>
                <p className={classes.mainText}>{job.seniority.charAt(0).toUpperCase() + job.seniority.slice(1)}</p>

                <div className={classes.technologies}>
                    {job.prerequisites.split(',').map((technology) => {
                        return <div className={classes.technology} key={technology}>{technology}</div>
                    })}
                </div>

                <p className={classes.expiresIn}>{Math.ceil((job.expires - new Date().getTime()) / (1000 * 3600 * 24))} days left</p>
            </div>
            {
                publishToDislinkt ?
                    <button className={classes.button}
                        onClick={publishToDislinkt ? () => publish() : null}>
                        Publish
                    </button>
                    : null
            }
        </div>
    )
}

export default Job;