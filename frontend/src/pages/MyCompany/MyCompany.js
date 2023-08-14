import React, { useState, useEffect } from 'react';
import classes from './MyCompany.module.css';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import Caption from "../../components/Caption/Caption";
import AllJobs from '../../components/Jobs/AllJobs/AllJobs';

import { axiosInstance } from "../../api/AxiosInstance"

function MyCompany() {

    const [company, setCompany] = useState();
    const [jobs, setJobs] = useState();
    const [editMode, setEditMode] = useState(false);

    const user = useSelector((state) => state.user.value);

    useEffect(() => {
        const config = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${user.accessToken}`
            }
        }

        axiosInstance.get(`/auth/whoami`, config)
            .then((response) => {
                axiosInstance.get(`/company/${response.data.email}`)
                    .then((response1) => {
                        setCompany(response1.data);
                    })

                axiosInstance.get(`/job-offer/all/${response.data.email}`)
                    .then((response1) => {
                        setJobs(response1.data);
                    })
            })
    },[]);


    function handleChange(e) {
        setCompany(prevState => ({ ...prevState, [e.target.name]: e.target.value }))
        console.log(e.target.value)
    }

    function handleEditProfile(e) {
        e.preventDefault();

        if (!editMode) {
            setEditMode(true);
            return;
        }

        console.log(company);

        axiosInstance.put(`/company`, company)
            .then((res) => {
                console.log(res.data);
            });

        setEditMode(false);
    }

    const navigate = useNavigate();

    function newJobOffer(){
        navigate(`/job-offer`)
    }

    function handlePublishToDislinkt() {
        console.log("ZEZ");
    }

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="My Company" />
            </div>
            <div>
                <div className={classes.descriptionWrapper}>
                    <div>
                        <div className={classes.descriptionQuestion}>Main information</div>
                        <div className={classes.descriptionUnderline}></div>
                    </div>
                    <div className={classes.buttons}>
                        <button className={classes.button}>Change password</button>
                        <button className={classes.button} onClick={handleEditProfile}>
                            {editMode ? "Save changes" : "Edit profile"}
                        </button>
                    </div>
                </div>
                <form className={classes.content}>
                    <div className={classes.contentRow}>
                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Company Name</p>
                            <input type="text" placeholder='Company Name' disabled={!editMode}
                                className={classes.input} required
                                name="name" value={company ? company.name : ''} onChange={handleChange} />
                        </div>

                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Email</p>
                            <input type="text" placeholder='Email' disabled={true}
                                className={classes.input} required
                                name="email" value={company ? company.email : ''} />
                        </div>

                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Industry</p>
                            <input type="text" placeholder='Industry' disabled={!editMode}
                                className={classes.input} required
                                name="industry" value={company ? company.industry : ''} onChange={handleChange} />
                        </div>

                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Origin</p>
                            <input type="text" placeholder='Origin' disabled={!editMode}
                                className={classes.input} required
                                name="origin" value={company ? company.origin : ''} onChange={handleChange} />
                        </div>

                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Website</p>
                            <input type="text" placeholder='Website' disabled={!editMode}
                                className={classes.input} required
                                name="website" value={company ? company.website : ''} onChange={handleChange} />
                        </div>

                    </div>
                    <div className={classes.contentRow}>

                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Offices in</p>
                            <input type="text" placeholder='Offices in' disabled={!editMode}
                                className={classes.input} required
                                name="cities" value={company ? company.cities : ''} onChange={handleChange} />
                        </div>

                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Number of Employees</p>
                            <input type="text" placeholder='Number of Employees' disabled={!editMode}
                                className={classes.input} required
                                name="size" value={company ? company.size : ''} onChange={handleChange} />
                        </div>


                        <div className={classes.inputForm}>
                            <p className={classes.inputLabel}>Description</p>
                            <textarea type="text" placeholder='Description' disabled={!editMode}
                                className={classes.textarea} required
                                name="description" value={company ? company.description : ''} onChange={handleChange} />
                        </div>
                    </div>
                </form>

                <div className={classes.descriptionWrapper}>
                    <div>
                        <div className={classes.descriptionQuestion}>Job Offers</div>
                        <div className={classes.descriptionUnderline}></div>
                    </div>
                    <button className={classes.button} onClick={newJobOffer}>Create job offer</button>
                </div>

                <AllJobs jobs={jobs ? jobs : []} publishToDislinkt={handlePublishToDislinkt}/>
            </div>
        </div>
    )
}

export default MyCompany