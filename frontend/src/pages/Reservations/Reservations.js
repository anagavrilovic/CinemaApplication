import classes from "./Reservations.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";

import { CheckUserPermission } from '../../components/Permissions/CheckUserPermission.js';

import { axiosInstance } from "../../api/AxiosInstance";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

function Reservations() {

    const [reservations, setReservations] = useState([]);
    const user = useSelector((state) => state.user.value);

    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.accessToken}`
        }
    }

    useEffect(() => {
        axiosInstance.get("reservation", config)
        .then((response) => {
            setReservations(response.data);
        })
        .catch((error) => console.log("Error loading reservations..."));
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Reservations" />
                <Search placeholder="Search reservations..." />
            </div>

            <CheckUserPermission role="['ROLE_ADMIN']">
                <button className={classes.button}><FontAwesomeIcon icon={faPlus} /> Add new reservation</button>
            </CheckUserPermission>

            
        </div>
    );
}

export default Reservations;