import classes from "./Users.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";

import { axiosInstance } from "../../api/AxiosInstance";

function Users() {

    const [users, setUsers] = useState([]);
    const user = useSelector((state) => state.user.value);

    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.accessToken}`
        }
    }

    useEffect(() => {
        axiosInstance.get("user", config)
        .then((response) => {
            setUsers(response.data);
        })
        .catch((error) => console.log("Error loading users..."));
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Users" />
                <Search placeholder="Search users..." />
            </div>

            <table className={classes.table}>
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Username</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        users.map((user, index) => 
                            <tr key={index}>
                                <td>{user.firstName}</td>
                                <td>{user.lastName}</td>
                                <td>{user.email}</td>
                                <td>{user.username}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
            
        </div>
    );
}

export default Users;