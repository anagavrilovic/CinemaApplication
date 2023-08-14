import React from 'react';
import classes from './CompanyProfileInterview.module.css';
import StarRate from '../../../StarRate/StarRate';

function CompanyProfileInterview({comments}) {


    function formatOfferStatus(status){
        status = status.charAt(0).toUpperCase() + status.slice(1);
        status = status.replaceAll("_", " ");
        return status;
    }

    return (
        <div className={classes.comments}>
            {comments.map((comment) => {
                return <div key={comment.id} className={classes.comment}>
                    <div className={classes.commentHeader}>
                        <div className={classes.commentRate}>
                            <p className={classes.rate}>{comment.mark}</p>
                            <StarRate rate={comment.mark}/>
                        </div>
                        <div className={classes.caption}>
                            <h3 className={classes.captionText}>{comment.caption}</h3>
                            <p className={classes.date}>{new Date(comment.creationDate).toLocaleDateString()}</p>
                        </div>
                    </div>
                    <div className={classes.commentContent}>
                        <p className={classes.boldText}>HR Interview</p>
                        <p className={classes.commentText}>{comment.hrInterviewImpression}</p>
                        <p className={classes.boldText}>Technical Interview</p>
                        <p className={classes.commentText}>{comment.technicalInterviewImpression}</p>
                        <p className={classes.authorInfo}>{comment.user.firstName} {comment.user.lastName}</p>
                        <p className={classes.authorInfo}>{formatOfferStatus(comment.offerStatus)}</p>
                    </div>
                </div>
            })}
        </div>
    )
}

export default CompanyProfileInterview