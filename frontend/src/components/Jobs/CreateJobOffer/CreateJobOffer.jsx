import React, { useEffect, useState } from 'react'
import classes from './CreateJobOffer.module.css';
import { useForm } from 'react-hook-form';
import TextareaAutosize from 'react-textarea-autosize';
import { useNavigate } from 'react-router';
import CompanyService from '../../../services/CompanyService';
import JobOfferService from '../../../services/JobOfferService';
import { useSelector } from 'react-redux';
import { axiosInstance } from "../../../api/AxiosInstance"

const CreateJobOffer = () => {

    const { register, handleSubmit, formState: { errors } } = useForm({})
    const navigate = useNavigate()
    const [company, setCompany] = useState([])
    const user = useSelector((state) => state.user.value);

    const onSubmit = handleSubmit((data) => {
        console.log(data)
        const jobOffer = {
            position: data.position,
            jobDescription: data.description,
            prerequisites: data.prerequisites,
            company: company,
            employment_type: parseInt(data.type),
            seniority: parseInt(data.seniority)
        }
        console.log(jobOffer)
        JobOfferService.save(jobOffer).then(resp => {
            console.log(resp.data)
            navigate('/my-company')
        })
    })

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
            })
    }, [])

    function getBack() {
        navigate('/my-company')
    }

    return (
        <div className={classes.page}>
            <div className={classes.form}>
                <form onSubmit={onSubmit}>
                    <div className={classes.formItem}>
                        <label htmlFor="func" className={classes.formLabel}>
                            Job position
                        </label>
                        <input
                            {...register("position", { required: { value: true, message: 'Position is required' } })}
                            type="text"
                            placeholder="Job title"
                        />
                        {
                            errors.position && <div className={classes.error}><label>{errors.position.message}</label></div>
                        }
                    </div>
                    <div className={classes.formItem}>
                        <label htmlFor="func" className={classes.formLabel}>
                            Job description
                        </label>
                        <TextareaAutosize
                            className={classes.textarea}
                            {...register("description", { required: { value: true, message: 'Description is required' } })}
                            type="text"
                            placeholder="Job description"
                        />
                        {
                            errors.description && <div className={classes.error}><label>{errors.description.message}</label></div>
                        }
                    </div>
                    <div className={classes.formItem}>
                        <label htmlFor="func" className={classes.formLabel}>
                            Prerequisites
                        </label>
                        <TextareaAutosize
                            className={classes.textarea}
                            {...register("prerequisites", { required: { value: true, message: 'Prerequisites is required' } })}
                            type="text"
                            placeholder="Prerequisites"
                        />
                        {
                            errors.prerequisites && <div className={classes.error}><label>{errors.prerequisites.message}</label></div>
                        }
                    </div>
                    <div className={classes.formItem}>
                        <label htmlFor="func" className={classes.formLabel}>
                            Employment type
                        </label>
                        <select name="func"
                            className={classes.select}
                            {...register("type", { required: { value: true, message: 'Type is required' } })}>
                            <option value=""></option>
                            <option value="0">Full time</option>
                            <option value="1">Part time</option>
                            <option value="2">Internship</option>
                        </select>
                        {
                            errors.type && <div className={classes.error}><label>{errors.type.message}</label></div>
                        }
                    </div>
                    <div className={classes.formItem}>
                        <label htmlFor="func" className={classes.formLabel}>
                            Seniority
                        </label>
                        <select name="func"
                            className={classes.select}
                            {...register("seniority", { required: { value: true, message: 'Feld is required' } })}>
                            <option value=""></option>
                            <option value="0">Junior</option>
                            <option value="1">Medior</option>
                            <option value="2">Senior</option>
                        </select>
                        {
                            errors.seniority && <div className={classes.error}><label>{errors.seniority.message}</label></div>
                        }
                    </div>
                    <div className={classes.formItem}>
                        <label htmlFor="func" className={classes.formLabel}>
                            Company
                        </label>
                        <label className={classes.companyLabel}>{company.name}</label>
                    </div>

                    <button className={classes.buttonLogIn}>Create job offer</button>
                    <button className={classes.buttonBack} onClick={getBack}>Back</button>
                </form>
            </div>
        </div>
    )
}

export default CreateJobOffer