import axios from 'axios';

const RegistrationService = {

    register: function(registrationData) {
        return axios.post(`http://localhost:8088/auth/register`, registrationData, {
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
        })

    }


}
export default RegistrationService;