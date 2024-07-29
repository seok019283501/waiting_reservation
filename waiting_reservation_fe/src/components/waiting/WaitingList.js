import { useEffect, useState,useContext } from 'react';
import '../../styles/Waiting.css'
import CommonItem from './CommonItem';
import axios from "axios";
import { useLocation } from 'react-router-dom';
import WaitingReservationContext from "../provider/WaitingReservationContext";
const WaitingList = (props) =>{
  const location = useLocation();

  const {waitingUserList,userList,reaminCount} = useContext(WaitingReservationContext);
  useEffect(()=>{
    waitingUserList(location.state.id);
  },[])

  



  return (
    <>
      <div className="Waiting-container">
        <div className='Waiting-count-container'>
          <div className='Waiting-count-sub-container'>
            <div className='Waiting-count-info-container'>
              {
                reaminCount === "" || reaminCount ===undefined ? 
                  '-' : `남은 순서 : ${reaminCount}`
              }
            </div>
            <div className='Waiting-count-info-button-container'>
            <input type='button' className='count-btn' onClick={()=>waitingUserList(location.state.id)} value={'조회'}/>
            </div>
          </div>
        </div>
        <div className='Waiting-menu-order-container'>
          <div className='Waiting-menu-list-container'>
              {
                userList.map((item)=>(
                  <CommonItem key={item.id} item={item}/>
                ))
              }
          </div>
        </div>
        
      </div>
    </>
  );
}

export default WaitingList;