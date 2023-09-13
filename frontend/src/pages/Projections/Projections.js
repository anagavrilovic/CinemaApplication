import classes from "./Projections.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";

import { CheckUserPermission } from '../../components/Permissions/CheckUserPermission.js';

import { axiosInstance } from "../../api/AxiosInstance";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

function Projections() {

    const [projections, setProjections] = useState([]);
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
        })
        .catch((error) => console.log("Error loading projections..."));
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Projections" />
                <Search placeholder="Search projections..." />
            </div>

            <CheckUserPermission role="['ROLE_ADMIN']">
                <button className={classes.button}><FontAwesomeIcon icon={faPlus} /> Add new projection</button>
            </CheckUserPermission>

            
        </div>
    );
}

export default Projections;