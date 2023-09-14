import classes from "./Projections.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { CheckUserPermission } from '../../components/Permissions/CheckUserPermission.js';

import { axiosInstance } from "../../api/AxiosInstance";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus, faTrash, faBookmark } from "@fortawesome/free-solid-svg-icons";

function Projections() {

    const [projections, setProjections] = useState([]);

    const navigate = useNavigate();

    const user = useSelector((state) => state.user.value);

    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.accessToken}`
        }
    }

    useEffect(() => {
        axiosInstance.get("projection", config)
        .then((response) => {
            setProjections(response.data);
            console.log(user.accessToken)
        })
        .catch((error) => console.log("Error loading projections..."));
    }, [])

    function handleCheckboxFilterChange(e) {
        if(e.target.checked) {
            axiosInstance.get("projection/available", config)
            .then((response) => {
                setProjections(response.data);
            })
            .catch(() => console.log("Error loading projections..."));
        } else {
            axiosInstance.get("projection", config)
            .then((response) => {
                setProjections(response.data);
            })
            .catch(() => console.log("Error loading projections..."));
        }
    }

    function handleReserveTicketsClick(projectionId) {
        navigate(`/reserveTickets/${projectionId}`);
    }

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Projections" />
                <Search placeholder="Search projections..." />
            </div>

            <CheckUserPermission role="['ROLE_ADMIN']">
                <button className={classes.button}><FontAwesomeIcon icon={faPlus} /> Add new projection</button>
            </CheckUserPermission>

            <label className={classes.filter}>
                <input type="checkbox" name="availableProjections" onChange={handleCheckboxFilterChange}/>
                Available Projections
            </label>

            <table className={classes.table}>
                <thead>
                    <tr>
                        <th>Movie</th>
                        <th>Movie Name</th>
                        <th>Theater</th>
                        <th>Start Date and Time</th>
                        <th>Length (minutes)</th>
                        <th>Ticket Price</th>
                        <th>Number of Available Seats</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                        projections.map((projection, index) => 
                            <tr key={index}>
                                <td>{projection.movieDto.id}</td>
                                <td>{projection.movieDto.name}</td>
                                <td>{projection.theaterDto.name}</td>
                                <td>{new Date(projection.startDateAndTime).toLocaleString('en-GB')}</td>
                                <td>{projection.movieDto.length}</td>
                                <td>{projection.ticketPrice}</td>
                                <td>{projection.numberOfAvailableSeats}</td>
                                <td>
                                    <CheckUserPermission role="['ROLE_USER']">
                                        <button className={classes.buttonReserve} onClick={() => handleReserveTicketsClick(projection.id)} 
                                            disabled={projection.numberOfAvailableSeats === 0}>
                                            <FontAwesomeIcon icon={faBookmark}/> 
                                            Reserve tickets
                                        </button>
                                    </CheckUserPermission>
                                </td>
                                <td>
                                    <CheckUserPermission role="['ROLE_ADMIN']">
                                        <button className={classes.buttonDelete}><FontAwesomeIcon icon={faTrash} /></button>
                                    </CheckUserPermission>
                                </td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    );
}

export default Projections;