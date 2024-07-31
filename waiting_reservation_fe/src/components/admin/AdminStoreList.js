import { useEffect, useState ,useContext} from 'react';
import '../../styles/AdminStoreList.css'
import axios from "axios";
import AdminStoreListItem from './AdminStoreListItem';
import WaitingReservationContext from "../provider/WaitingReservationContext";
const AdminStoreList = (props) =>{
  const list = ["전체","서울특별시","경기도","인천광역시","충청남도","충청북도","대전광역시",
    "강원도","제주도","전라북도","전라남도","광주광역시","경상북도","경상남도","부산광역시","울산광역시"];
  

  const typeList = ["all","on","off"];

  const [address, setAddress] = useState("전체");
  const [storeName, setStoreName] = useState("");
  const [storeList, setStoreList] = useState([]);
  const [type, setType] = useState("all");

  const {role} = useContext(WaitingReservationContext);

  //승인 유무
  const handleSetType= (e) =>{
    setType(e.target.value);
  }

  //주소
  const handleSetAddress= (e) =>{
    setAddress(e.target.value);
  }

  //식당 이름
  const handleSetStoreName = (e) =>{
    setStoreName(e.target.value);
  }

  //식당 검색
  const search = () => {
    if(role !== "ROLE_OWNER"){
      openSearch();
    }
    
  }

  useEffect(()=>{
    openSearch()
    
  },[])

  //식당 전체 검색
  const openSearch=()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/admin/list/${type}`),{
      headers:{
        Authorization: tk
      }
    })
    .then(res=>{
      setStoreList(res.data.body);
    }).catch(err=>{
      console.log(err);
    })
  }

  // 사업자 식당 리스트
  const ownerStoreSearch=()=>{
    const tk = localStorage.getItem("jwt")
    axios.get((`http://localhost:8080/api/store/search?${storeName !== '' ? `store_name=${storeName}` : null}&${address !== '전체' ? `address=${address}` : null}`),{
      headers:{
        Authorization: tk
      }
    })
    .then(res=>{
      setStoreList(res.data.body);
    }).catch(err=>{
      console.log(err);
    })
  }
  
  return (
    <>
      <div className="AdminStoreList-container">
        <div className="AdminStoreList-serch-container">
          <div className="AdminStoreList-serch-input-container">
            <input type="text" className="AdminStoreList-serch" onChange={handleSetStoreName} placeholder="식당 이름을 적어주세요."/>
            <input type="button" className="AdminStoreList-serch-btn" onClick={search} value={"검색"}/>
          </div>
          <div className='AdminStoreList-serch-select-address-container'>
            <select onChange={handleSetAddress} value={address}>
              {
                list.map((item,index)=>(
                  <option key={index} value={item}>
                    {item}
                  </option>
                ))
              }
            </select>
            <select onChange={handleSetType} value={type}>
              {
                typeList.map((item,index)=>(
                  <option key={index} value={item}>
                    {item}
                  </option>
                ))
              }
            </select>
          </div>
        </div>
          <div className="AdminStoreList-list-container">
            <div className='AdminStoreList-list-sub-container'>
              {
                storeList.map((item)=>(
                  <AdminStoreListItem key={item.id} item={item} openSearch={openSearch}/>
                ))
              }
            </div>
          </div>
        
      </div>
    </>
  );
}

export default AdminStoreList;