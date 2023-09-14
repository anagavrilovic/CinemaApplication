import React from 'react';
import { useState } from 'react';
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";

import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import classes from "./ReserveTickets.module.css"
import { axiosInstance } from "../../api/AxiosInstance";

import Caption from '../../components/Caption/Caption'
import schema from "../../validationSchemas/ReserveTicketsValidationSchema";


function ReserveTickets() {

    const navigate = useNavigate();

    const { id } = useParams();

    const user = useSelector((state) => state.user.value);

    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.accessToken}`
        }
    }

    const [serverError, setServerError] = useState(null);

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(schema)
    });

    function reservationHandler(data) {

        const reservationDto = {
            projectionId: parseInt(id),
            numberOfTickets: data.numberOfTickets
        }

        axiosInstance.post("reservation/reserve", reservationDto, config)
        .then(() => {
            navigate("/reservations");
        })
        .catch((error) => {
            setServerError(error.response.data.message);
        })
            
    }

    return (
        <div className={classes.page}>
            <Caption caption="Reserve Tickets"/>

            <form onSubmit={handleSubmit(reservationHandler)} className={classes.form}>

                <div className={classes.formItem}>
                    <input type='number' placeholder='Number of tickets' min="1"
                        className={errors.numberOfTickets ? classes.errorInput : ''}
                        {...register("numberOfTickets")} />
                    <div className={classes.errorMessage}>{errors.numberOfTickets?.message}</div>
                </div>

                <div className={classes.serverErrorMessage}>
                        {serverError ? serverError : ""}</div>
                <button className={classes.button}>Make a reservation</button>
            </form>
        </div>
    )
}

export default ReserveTickets