import { useLocation } from 'react-router-dom';
import { Route, Routes } from 'react-router-dom';

import Homepage from './pages/Homepage/Homepage';
import Mainpage from './pages/Mainpage/Mainpage';
import Navbar from './components/Navbar/Navbar';
import Movies from './pages/Movies/Movies';
import './App.css';
import CompanyProfile from './components/Company/CompanyProfile/CompanyProfile/CompanyProfile';
function App() {

    const location = useLocation();

    return (
        <div className="App">
            { location.pathname !== '/' ? <Navbar /> : null }
            <Routes>
                <Route path='/' element={ <Mainpage /> } />
                <Route path='/home' element={ <Homepage /> } />
                <Route path='/movies' element={ <Movies /> } />
                {/* <Route path='/requests' element={ <ProtectedRoute Component = {RegistrationRequests} Roles="['ROLE_ADMIN']" /> } /> */}
                <Route path='/company/:id/:name' element={ <CompanyProfile /> } />
            </Routes>
        </div>
    );
}

export default App;
