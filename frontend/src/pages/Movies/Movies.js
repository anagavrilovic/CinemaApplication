import classes from "./Movies.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";

import { CheckUserPermission } from '../../components/Permissions/CheckUserPermission.js';

import { axiosInstance } from "../../api/AxiosInstance";
import AllMovies from "../../components/Movie/AllMovies/AllMovies";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

function Movies() {

    const [movies, setMovies] = useState([]);
    const user = useSelector((state) => state.user.value);

    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${user.accessToken}`
        }
    }

    useEffect(() => {
        axiosInstance.get("movie", config)
        .then((response) => {
            setMovies(response.data);
        })
        .catch((error) => console.log("Error loading movies..."));
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Movies" />
                <Search placeholder="Movie name, genre or key word..." />
            </div>

            <CheckUserPermission role="['ROLE_ADMIN']">
                <button className={classes.button}><FontAwesomeIcon icon={faPlus} /> Add new movie</button>
            </CheckUserPermission>

            <div className={classes.content}>
                <div className={classes.movies}>
                    <AllMovies movies={movies} />
                </div>
            </div>
        </div>
    );
}

export default Movies;