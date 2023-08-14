import classes from './Homepage.module.css';
import {faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function Homepage() {

    return (
        <div className={classes.page}>
            <div className={classes.search}>
                <h1 className={classes.caption}> Grab your popcorn and immerse yourself in the magic of movies</h1>
                <div className={classes.formItem}>
                    <FontAwesomeIcon icon={faMagnifyingGlass} className={classes.icon}/>
                    <input type='text' placeholder='Search movies...' />
                </div>
            </div>
        </div>
    );
}

export default Homepage;