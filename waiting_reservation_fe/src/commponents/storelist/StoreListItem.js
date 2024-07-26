import { useEffect, useState } from 'react';
import '../../styles/StoreListItem.css'
import axios from "axios";
const StoreListItem = (props) =>{
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
      setStatus(true);
    }).catch(err=>{
      console.log(err);
    })
  }

  //예약 유무 확인
  useEffect(()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/reservation/remain/${props.item.id}`),{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      setStatus(true);
    }).catch(err=>{
      console.log(err);
    })
  },[])
  
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
            <input type='button' disabled={status} className='reservation-btn' onClick={reservation} value={status ? "예약중" : "예약"}/>
          </div>
        </div>
      </div>
    </>
  );
}

export default StoreListItem;