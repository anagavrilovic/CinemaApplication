import React, { useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import classes from './InteractiveStarRate.module.css';

const InteractiveStarRate = ({ratingChanged}) => {

    const [rate, setRate] = useState(0);

    function onRatingChanged(value){
        setRate(value);
        ratingChanged(value);
    }

    return (
        <span>
            <FontAwesomeIcon icon={faStar} className={rate > 0 ? classes.starYes : classes.starNo} onClick={() => onRatingChanged(1)} />
            <FontAwesomeIcon icon={faStar} className={rate > 1.5 ? classes.starYes : classes.starNo} onClick={() => onRatingChanged(2)} />
            <FontAwesomeIcon icon={faStar} className={rate > 2.5 ? classes.starYes : classes.starNo} onClick={() => onRatingChanged(3)} />
            <FontAwesomeIcon icon={faStar} className={rate > 3.5 ? classes.starYes : classes.starNo} onClick={() => onRatingChanged(4)} />
            <FontAwesomeIcon icon={faStar} className={rate > 4.5 ? classes.starYes : classes.starNo} onClick={() => onRatingChanged(5)} />
        </span>
    )
}

export default InteractiveStarRate