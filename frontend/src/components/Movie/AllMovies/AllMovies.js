import React from 'react';
import classes from './AllMovies.module.css';
import Movie from '../Movie/Movie';

function AllMovies({movies}) {

    return (
        <div className={classes.component} > 
        {
            movies.map((movie) => {
                return <Movie key={movie.id} movie={movie} />
            })
        }
        </div>
    );
}

export default AllMovies;