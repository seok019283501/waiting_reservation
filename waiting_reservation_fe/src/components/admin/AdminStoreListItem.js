import { useEffect, useState, useContext, useCallback } from 'react';
import { useNavigate } from "react-router-dom";
import '../../styles/AdminStoreListItem.css'
import WaitingReservationContext from "../provider/WaitingReservationContext";
import axios from "axios";
const AdminStoreListItem = (props) =>{

  const [status,setState] = useState(true);
  //예약
  const handleSetStatus = useCallback((e) =>{
    const tk = localStorage.getItem("jwt")
    axios.put((`http://localhost:8080/api/admin/store/${props.item.id}?status=${e.target.value ==="승인" ? "STATUS_ON" : "STATUS_OFF"}`),{},{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
      props.openSearch();
    }).catch(err=>{
      console.log(err);
    })
  },[props])
  
  
  return (
    <>
      <div className="AdminStoreListItem-container">
        <div className="AdminStoreListItem-main-container">
          <div className="AdminStoreListItem-info-container">
            <div className="AdminStoreListItem-store-name-container">
              <div>{props.item.storeName}</div>
              <div style={{color:`${props.item.status ==="STATUS_ON" ? 'blue':'red'}`}}>{props.item.status ==="STATUS_ON" ? "승인" : "미승인"}</div>
            </div>
            <div className="AdminStoreListItem-addresse-container">
              <div>{props.item.address}</div>
            </div>
          </div>
          <div className="AdminStoreListItem-status-container">
            <input type='button' className='status-btn' onClick={handleSetStatus} value={props.item.status ==="STATUS_ON" ? "미승인" : "승인"}/>
          </div>
        </div>
      </div>
    </>
  );
}

export default AdminStoreListItem;