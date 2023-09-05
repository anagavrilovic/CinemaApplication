import React, {useEffect} from 'react'
import classes from './Movie.module.css'
import MovieImage from '../../../images/movie.jpeg';
import { useNavigate } from 'react-router-dom';

function Movie(movie) {

    const navigate = useNavigate();

    function handleViewCompany() {
        navigate(`/projections/${movie.id}`, {state: {movie: movie }});
    }

    useEffect(() => {
        console.log(movie)
    }, [])

    return (
        <div className={classes.component}>
            <div className={classes.movieName}>
                <img src={MovieImage} alt={movie.movie.name} />
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
                        <p className={classes.textBold}>{movie.movie.genres}</p>
                        <p className={classes.textBold}>{movie.movie.length}</p>
                    </div>
                </div>
                <div className={classes.description}>{movie.movie.description}</div>
            </div>
            <div className={classes.buttonDetails}>
                <button className={classes.button} onClick={handleViewCompany}>View Projections</button>
            </div>
        </div>
    );
}

export default Movie;