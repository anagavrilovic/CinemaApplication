import { useLocation } from 'react-router-dom';
import { Route, Routes } from 'react-router-dom';

import ProtectedRoute from './ProtectedRoute'
import Homepage from './pages/Homepage/Homepage';
import Mainpage from './pages/Mainpage/Mainpage';
import Navbar from './components/Navbar/Navbar';
import Movies from './pages/Movies/Movies';
import './App.css';
import CompanyProfile from './components/Company/CompanyProfile/CompanyProfile/CompanyProfile';
import Theaters from './pages/Theaters/Theaters';
import Projections from './pages/Projections/Projections';
import Reservations from './pages/Reservations/Reservations';
import Users from './pages/Users/Users';
function App() {

    const location = useLocation();

    return (
        <div className="App">
            { location.pathname !== '/' ? <Navbar /> : null }
            <Routes>
                <Route path='/' element={ <Mainpage /> } />
                <Route path='/home' element={ <ProtectedRoute Component = {Homepage} Roles="['ROLE_ADMIN', 'ROLE_USER']" /> } />
                <Route path='/movies' element={ <ProtectedRoute Component = {Movies} Roles="['ROLE_ADMIN', 'ROLE_USER']" /> } />
                <Route path='/theaters' element={ <ProtectedRoute Component = {Theaters} Roles="['ROLE_ADMIN', 'ROLE_USER']" /> } />
                <Route path='/projections' element={ <ProtectedRoute Component = {Projections} Roles="['ROLE_ADMIN', 'ROLE_USER']" /> } />
                <Route path='/reservations' element={ <ProtectedRoute Component = {Reservations} Roles="['ROLE_ADMIN', 'ROLE_USER']" /> } />
                <Route path='/users' element={ <ProtectedRoute Component = {Users} Roles="['ROLE_ADMIN']" /> } />
                <Route path='/company/:id/:name' element={ <CompanyProfile /> } />
            </Routes>
        </div>
    );
}

export default App;
