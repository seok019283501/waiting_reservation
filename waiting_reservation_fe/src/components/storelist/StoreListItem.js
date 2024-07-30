import { useEffect, useState, useContext } from 'react';
import { useNavigate } from "react-router-dom";
import '../../styles/StoreListItem.css'
import WaitingReservationContext from "../provider/WaitingReservationContext";
import axios from "axios";
const StoreListItem = (props) =>{
  const {role} = useContext(WaitingReservationContext);

  const navigator = useNavigate();

  //예약 유무
  const [status,setStatus] = useState(false);
  //예약
  const reservation = () =>{
    const tk = localStorage.getItem("jwt")
    axios.post((`http://localhost:8080/api/reservation/${props.item.id}`),{},{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      props.handleSuccessAlert(res);
      setStatus(true);
    }).catch(err=>{
      console.log(err);
      props.handleFailAlert(err);
    })
  }

  
  useEffect(()=>{
    if(role !== "ROLE_OWNER"){
      reservationStatus();

    }
  },[])

  //예약 유무 확인
  const reservationStatus=()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/reservation/info/${props.item.id}`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setStatus(true);
    }).catch(err=>{
      console.log(err);
    })
  }
  
  return (
    <>
      <div className="StoreListItem-container">
        <div className="StoreListItem-main-container">
          <div className="StoreListItem-info-container">
            <div className="StoreListItem-store-name-container">
              <div>{props.item.storeName}</div>
            </div>
            <div className="StoreListItem-addresse-container">
              <div>{props.item.address}</div>
            </div>
          </div>
          <div className="StoreListItem-reservation-container">
            {
              role !== "ROLE_OWNER" ? 
              <input type='button' disabled={status} className='reservation-btn' onClick={reservation} value={status ? "예약중" : "예약"}/>
              :<>
                <input type='button' className='reservation-btn' onClick={()=>navigator("waiting/list",{state:{id:props.item.id}})} value={"목록"}/>
                <input type='button' className='reservation-btn' onClick={()=>navigator("/menu/add",{state:{storeId:props.item.id}})} value={"메뉴 추가"}/>
              </>  
            }
          </div>
        </div>
      </div>
    </>
  );
}

export default StoreListItem;