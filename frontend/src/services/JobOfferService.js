import axios from 'axios';

const JobOfferService = {

    save: function(data) {
        return axios.post(`http://localhost:8088/job-offer`,data, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
        })

    },

    sendToDislinkt: function(data){
        return axios.post(`https://localhost:8083/receive-job-offer`,data, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
        })
    },

    sendOffer: function(id,email) {
        console.log(localStorage.getItem('token'))
        return axios.post(`http://localhost:8088/job-offer/`+id+"/send-token/"+email, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
        })

    }


}
export default JobOfferService;