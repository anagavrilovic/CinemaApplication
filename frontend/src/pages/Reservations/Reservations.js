import classes from "./Reservations.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";

import { axiosInstance } from "../../api/AxiosInstance";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan } from "@fortawesome/free-solid-svg-icons";

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
        console.log(user)
        if(user.role === 'ADMIN') {
            axiosInstance.get("reservation", config)
            .then((response) => {
                setReservations(response.data);
            })
            .catch((error) => console.log("Error loading reservations..."));
        } else if(user.role === 'USER') {
            axiosInstance.get("reservation/user", config)
            .then((response) => {
                setReservations(response.data);
            })
            .catch((error) => console.log("Error loading reservations..."));
        }
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Reservations" />
                <Search placeholder="Search reservations..." />
            </div>

            { reservations.length !== 0 ? 
                <table className={classes.table}>
                    <thead>
                        <tr>
                            <th>User - First Name</th>
                            <th>User - Last Name</th>
                            <th>User - Email</th>
                            <th>Movie</th>
                            <th>Theater</th>
                            <th>Start Date and Time</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            reservations.map((reservation, index) => 
                                <tr key={index}>
                                    <td>{reservation.userDto.firstName}</td>
                                    <td>{reservation.userDto.lastName}</td>
                                    <td>{reservation.userDto.email}</td>
                                    <td>{reservation.projectionDto.movieDto.name}</td>
                                    <td>{reservation.projectionDto.theaterDto.name}</td>
                                    <td>{new Date(reservation.projectionDto.startDateAndTime).toLocaleString('en-GB')}</td>
                                    <td>
                                        <button className={classes.buttonDelete}><FontAwesomeIcon icon={faBan} /></button>
                                    </td>
                                </tr>
                            )
                        }
                    </tbody>
                </table> : 
                <p className={classes.noEntitiesMessage}>There are no active reservations...</p>
            }
        </div>
    );
}

export default Reservations;