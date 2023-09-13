import React, {useEffect} from 'react'
import classes from './Movie.module.css'
import { useNavigate } from 'react-router-dom';

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faTrash } from "@fortawesome/free-solid-svg-icons";

import { CheckUserPermission } from '../../../components/Permissions/CheckUserPermission.js';


function Movie(movie) {

    const navigate = useNavigate();

    // function handleViewCompany() {
    //     navigate(`/projections/${movie.id}`, {state: {movie: movie }});
    // }

    useEffect(() => {
    }, [])

    function formatGenres(genres) {
        var formatted = '';

        for(var genre of genres) {
            formatted = formatted.concat(genre.toLowerCase().replace("_", " ")).concat(", ");
        }

        formatted = formatted.slice(0, -2);

        return formatted;
    }

    return (
        <div className={classes.component}>
            <div className={classes.movieName}>
                <h3>{movie.movie.name}</h3>
            </div>
            <div className={classes.about}>
                <div className={classes.shortAbout}>
                    <div className={classes.shortAboutLabels}>
                        <p>Director:</p>
                        <p>Genres:</p>
                        <p>Length:</p>
                    </div>
                    <div className={classes.shortAboutValues}>
                        <p className={classes.textBold}>{movie.movie.director}</p>
                        <p className={classes.textBold}>{formatGenres(movie.movie.genres)}</p>
                        <p className={classes.textBold}>{movie.movie.length} minutes</p>
                    </div>
                </div>
                <div className={classes.description}>{movie.movie.description}</div>
            </div>
            <CheckUserPermission role="['ROLE_ADMIN']">
                <div className={classes.buttonDetails}>
                    <button className={classes.button}><FontAwesomeIcon icon={faPenToSquare} /></button>
                    <button className={classes.button}><FontAwesomeIcon icon={faTrash} /></button>
                </div>
            </CheckUserPermission>
        </div>
    );
}

export default Movie;