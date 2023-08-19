import classes from "./Movies.module.css";
import Caption from "../../components/Caption/Caption";
import Search from "../../components/Search/Search";
//import AllCompanies from "../../components/Company/AllCompanies/AllCompanies";
import React, { useEffect, useState } from 'react';
import { useSelector } from "react-redux";

import { axiosInstance } from "../../api/AxiosInstance";

function Movies() {

    const [movies, setMovies] = useState([]);
    const user = useSelector((state) => state.user.value);

    useEffect(() => {
        const config = {
            headers: {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${user.accessToken}`
            }
        }

        axiosInstance.get("/movie", config)
        .then((response) => {
            setMovies(response.data);
        })
        .catch(function (error) {
            if (error.response) {
              // The request was made and the server responded with a status code
              // that falls out of the range of 2xx
              console.log(error.response.data);
              console.log(error.response.status);
              console.log(error.response.headers);
            } else if (error.request) {
              // The request was made but no response was received
              // `error.request` is an instance of XMLHttpRequest in the browser 
              // and an instance of http.ClientRequest in node.js
              console.log(error.request);
            } else {
              // Something happened in setting up the request that triggered an Error
              console.log('Error', error.message);
            }
           
          });
    }, [])

    return (
        <div className={classes.page}>
            <div className={classes.header}>
                <Caption caption="Overview of All Movies" />
                <Search placeholder="Movie name, genre or key word" />
            </div>

            <div className={classes.content}>
                {/* <AllCompanies companies={companies}/> */}
            </div>
        </div>
    );
}

export default Movies;