import { useEffect, useRef, useState } from "react";
import WaitingReservationContext from "./WaitingReservationContext";
import axios from "axios";
const WaitingReservationProvider = (props) =>{
  
    

  return (
    <WaitingReservationContext.Provider>
        {props.children}
    </WaitingReservationContext.Provider>
  );
};

export default WaitingReservationProvider;