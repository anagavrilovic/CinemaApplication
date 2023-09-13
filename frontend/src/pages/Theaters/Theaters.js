import classes from "./Theaters.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";
import { axiosInstance } from "../../api/AxiosInstance";

function Theaters() {

    const [theaters, setTheaters] = useState([]);
    const user = useSelector((state) => state.user.value);

    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.accessToken}`
        }
    }

    useEffect(() => {
        axiosInstance.get("theater", config)
        .then((response) => {
            setTheaters(response.data);        })
        .catch((error) => console.log("Error loading theaters..."));
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Theaters" />
                <Search placeholder="Theater name or key word..." />
            </div>
        
            <table className={classes.table}>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Number of seats</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        theaters.map((theater, index) => 
                            <tr key={index}>
                                <td>{theater.name}</td>
                                <td>{theater.numberOfSeats}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    );
}

export default Theaters;