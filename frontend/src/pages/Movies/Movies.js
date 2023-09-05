import classes from "./Movies.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";

import { axiosInstance } from "../../api/AxiosInstance";
import AllMovies from "../../components/Movie/AllMovies/AllMovies";

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
                <Search placeholder="Movie name, genre or key word" />
            </div>

            <div className={classes.content}>
                <AllMovies movies={movies} />
            </div>
        </div>
    );
}

export default Movies;