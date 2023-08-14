import axios from 'axios';

const CompanyService = {

    getAll: function() {
        return axios.get(`http://localhost:8088/company/all`, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
        })

    }


}
export default CompanyService;