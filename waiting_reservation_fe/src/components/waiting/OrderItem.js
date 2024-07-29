import axios from "axios";
import '../../styles/OrderItem.css'
import {useContext, useState} from 'react';
import WaitingReservationContext from "../provider/WaitingReservationContext";
import { useNavigate } from "react-router-dom";
const OrderItem = (props) =>{


  return (
    <>
      <div className="OrderItem-container">
        <div className="OrderItem-main-container">
          <div className="OrderItem-info-container">
            <div className="OrderItem-menu-name-description-container">
              <div className="title">{props.item.title}</div>
              <div className="description">{props.item.count}개</div>
            </div>
            <div className="OrderItem-addresse-container">
              <div>{props.item.cost}원</div>
            </div>
            
          </div>
        </div>
      </div>
    </>
  );
}

export default OrderItem;