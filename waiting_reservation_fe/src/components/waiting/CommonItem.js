import axios from "axios";
import '../../styles/CommonItem.css'
import {useContext, useState} from 'react';
import WaitingReservationContext from "../provider/WaitingReservationContext";
import { useNavigate } from "react-router-dom";
const CommonItem = (props) =>{
  const navigator = useNavigate();
  const {role,waitingUserList,handleSetOrderMenuList} = useContext(WaitingReservationContext);
  const [count,setCount] = useState("");
  console.log(props)
  // ------------------
  // user
  const orderMenu = () =>{

    const menu = {
      id:props.item.id,
      count:count
    }
    handleSetOrderMenuList(menu);
  }

  const handleCount=(e)=>{
    setCount(Number(e.target.value))
  }



  // -------------------
  // owner
  const allow = () =>{
    const tk = localStorage.getItem("jwt")
    axios.put((`http://localhost:8080/api/reservation/complete/${props.item.storeId}/${props.item.id}`),{},{
      headers:{
        Authorization: tk
      }
    }).then(res=>{
      console.log(res)
    }).catch(err=>{
      console.log(err);
    })
    setTimeout(()=>{
      waitingUserList(props.item.storeId);
    },100)

  }


  return (
    <>
      <div className="CommonItem-container">
        <div className="CommonItem-main-container">
          <div className="CommonItem-info-container">
          {
            role === "ROLE_OWNER" ? 
            
            <>
              <div className="CommonItem-menu-name-description-container">
                <div className="title">이름 : {props.item.name}</div>
              </div>
              <div className="CommonItem-addresse-container">
                <div>{props.item.phoneNumber}</div>
              </div>
            </>
            :
            <>
              <div className="CommonItem-menu-name-description-container">
                <div className="title">{props.item.title}</div>
                <div className="description">{props.item.description}</div>
              </div>
              <div className="CommonItem-addresse-container">
                <div>{props.item.cost}</div>
              </div>


            </>
          }
            
          </div>
          
          <div className="CommonItem-reservation-container">
            {
              role !== "ROLE_OWNER" ? 
                <input type="number" onChange={handleCount} className='menu-order-count'/>
              : null
            }
            <input type='button' className='menu-btn' onClick={role !== "ROLE_OWNER" ? orderMenu : allow} value={role !== "ROLE_OWNER" ? "추가" : "허용"}/>
            {
              role === "ROLE_OWNER" ?
              <input type='button' className='menu-btn' onClick={()=>navigator('/waiting/order',{state:{
                storeId:props.item.storeId,
                username:props.item.username,
                name:props.item.name
              }})} value={"내역"}/>
              :null

            }
          </div>
        </div>
      </div>
    </>
  );
}

export default CommonItem;