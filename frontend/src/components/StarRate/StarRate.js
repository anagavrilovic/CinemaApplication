import React from 'react';
import classes from './StarRate.module.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';

function StarRate(props) {
    return (
        <span>
            <FontAwesomeIcon icon={faStar} className={props.rate > 0 ? classes.starYes : classes.starNo} />
            <FontAwesomeIcon icon={faStar} className={props.rate > 1.5 ? classes.starYes : classes.starNo} />
            <FontAwesomeIcon icon={faStar} className={props.rate > 2.5 ? classes.starYes : classes.starNo} />
            <FontAwesomeIcon icon={faStar} className={props.rate > 3.5 ? classes.starYes : classes.starNo} />
            <FontAwesomeIcon icon={faStar} className={props.rate > 4.5 ? classes.starYes : classes.starNo} />
        </span>
    )
}

export default StarRate