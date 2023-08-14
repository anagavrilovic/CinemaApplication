import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

function GuardedRoute (props) {
    
    const loggedUser = useSelector(state => state.user.value);
    if (!loggedUser.role) 
        return <Navigate to="/" />

    const isAuthorized = props.Roles.includes(loggedUser.role);
    return isAuthorized ? <props.Component /> : <Navigate to="/" />
};

export default GuardedRoute;