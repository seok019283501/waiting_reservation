import WaitingReservationProvider from "./components/provider/WaitingReservationProvider";
import WaitingReservationTemplete from "./components/main/WaitingReservationTemplete";
import StoreList from "./components/storelist/StoreList";
import WaitingInfo from "./components/waiting/WaitingInfo";
import WaitingList from "./components/waiting/WaitingList";
import Order from "./components/waiting/Order";
import AdminStoreList from "./components/admin/AdminStoreList";
import StoreRegist from "./components/storelist/StoreRegist";
import UserRegist from "./components/user/UserRegist";
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
function App() {
  return (
    
    <WaitingReservationProvider>
      <Router>
        <WaitingReservationTemplete>
          <Routes>
            <Route path="/" element={<StoreList/>}/>
            <Route path="/waiting" element={<WaitingInfo/>}/>
            <Route path="/waiting/list" element={<WaitingList/>}/>
            <Route path="/waiting/order" element={<Order/>}/>
            <Route path="admin/store" element={<AdminStoreList/>}/>
            <Route path="/store/regist" element={<StoreRegist/>}/>
            <Route path="/regist" element={<UserRegist/>}/>
          </Routes>
        </WaitingReservationTemplete>
      </Router>
    </WaitingReservationProvider>
    
  );
}

export default App;
