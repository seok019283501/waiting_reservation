import { useEffect, useState, useContext } from 'react';
import { useNavigate,useLocation } from "react-router-dom";
import '../../styles/MenuInsert.css'
import WaitingReservationContext from "../provider/WaitingReservationContext";
import axios from "axios";
import MenuListItem from './MenuListItem';
const MenuInsert = (props) =>{
  const {role} = useContext(WaitingReservationContext);
  const [addressOnOff,setAddressOnOff] = useState(false);
  const navigator = useNavigate();
  const [menuList,setMenulist] = useState([]);
  const location = useLocation();
  const [manuInsertTF,setMenuInsertTF] = useState(false);
  const [menu,setMenu] = useState({
    title:"",
    description:"",
    cost:0
  })

  useEffect(()=>{
    menuInfo();
  },[])

  //메뉴 정보 조회
  const menuInfo = () =>{
    axios.get((`http://localhost:8080/open-api/menu/${location.state.storeId}`))
    .then(res=>{
      setMenulist(res.data.body)
    }).catch(err=>{
      console.log(err);
    })
  }

  const handleSetMenuName = (e) =>{
    setMenu({...menu,title:e.target.value});
  }
  
  const handleSetDescription = (e) =>{
    setMenu({...menu,description:e.target.value});
  }
  
  const handleSetPrice = (e) =>{
    setMenu({...menu,cost:isNaN(Number(e.target.value)) ? 0 : Number(e.target.value)});
  }

  const insertMenu = ()=> {
    const tk = localStorage.getItem("jwt")
    axios.post((`http://localhost:8080/api/menu/owner/${location.state.storeId}`),menu,{
      headers:{
        Authorization: tk
      }
    })
    .then(res=>{
      menuInfo();
    }).catch(err=>{
      console.log(err);
    })
  }

  //유효성
  useEffect(()=>{
    if(menu.cost !== "" && menu.description !== "" && menu.title !== ""){
      setMenuInsertTF(false);
    }else{
      setMenuInsertTF(true);
    }
  },[menu])
  
  
  return (
    <>
      <div className="MenuInsert-container">
        <div className='MenuInsert-main-container'>
          <div className='MenuInsert-left-container'>
            <div className='MenuInsert-info-container'>
              <div className='MenuInsert-info-sub-container'>
                <label htmlFor='menu-name'>메뉴명</label>
                <input type='text' name='menu-name' onChange={handleSetMenuName} value={menu.title} className='MenuInsert-menu-name-input' placeholder='식당 이름을 적어주세요.'/>
              </div>
            </div>
            <div className='MenuInsert-info-container'>
              <div className='MenuInsert-info-sub-container'>
                <label htmlFor='menu-description'>설명</label>
                <input type='text' name='menu-description' onChange={handleSetDescription} value={menu.description} className='MenuInsert-menu-description-input' placeholder='메뉴 설명을 적어주세요.'/>
              </div>
            </div>

            <div className='MenuInsert-info-container'>
              <div className='MenuInsert-info-sub-container'>
                <label htmlFor='menu-price'>가격</label>
                <input type='text' name='menu-price' onChange={handleSetPrice} value={menu.cost} className='MenuInsert-menu-price-input' placeholder='메뉴 가격을 적어주세요.'/>
              </div>
            </div>

            <div className='MenuInsert-info-container'>
              <div className='MenuInsert-btn-container'>
                  <input type='button' disabled={manuInsertTF} onClick={insertMenu} value={"등록"} className='regist-btn'/>
                </div>
            </div>
          </div>
          
          <div className='MenuInsert-right-container'>
            <div className='MenuInsert-right-sub-container'>
              {
                menuList.map((item, index)=>(
                  <MenuListItem key={index} item={item}/>
                ))
              }
            </div>
          </div>
          
        </div>
        
      </div>
    </>
  );
}

export default MenuInsert;