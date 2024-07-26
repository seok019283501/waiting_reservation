import WaitingReservationProvider from "./commponents/main/provider/WaitingReservationProvider";
import WaitingReservationTemplete from "./commponents/main/WaitingReservationTemplete";
import StoreList from "./commponents/storelist/StoreList";
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
function App() {
  return (
    
    <WaitingReservationProvider>
      <Router>
        <WaitingReservationTemplete>
          <Routes>
            <Route path="/" element={<StoreList/>}/>
          </Routes>
        </WaitingReservationTemplete>
      </Router>
    </WaitingReservationProvider>
    
  );
}

export default App;
