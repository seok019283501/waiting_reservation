import '../../styles/WaitingReservationTemplete.css'
import LeftSide from "./LeftSide";
const WaitingReservationTemplete = ({children}) =>{
  
  return (
    <>
      <div className="WaitingReservationTemplete-container">
        <div className="WaitingReservationTemplete-main-container">
          <div className="WaitingReservationTemplete-top-container">

          </div>
          <div className="WaitingReservationTemplete-middle-container">
            <div className="WaitingReservationTemplete-middle-left-container">
              <LeftSide/>
            </div>
            <div className="WaitingReservationTemplete-middle-right-container">
              <div className="WaitingReservationTemplete-contents-container">
                {children}
              </div>
            </div>
          </div>
        </div>
        

      </div>
    </>
  );
}

export default WaitingReservationTemplete;