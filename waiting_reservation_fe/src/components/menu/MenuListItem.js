import axios from "axios";
import '../../styles/MenuListItem.css'
const MenuListItem = (props) =>{

    //메뉴 삭제
    const menuDelete = () =>{
      const tk = localStorage.getItem("jwt")
      axios.delete((`http://localhost:8080/api/menu/owner/${props.item.storeId}/${props.item.id}`),{
        headers:{
          Authorization: tk
        }
      })
      .then(res=>{
        window.location.reload();
      }).catch(err=>{
        console.log(err);
      })
    }

  return (
    <>
      <div className="MenuListItem-container">
        <div className="MenuListItem-main-container">
          <div className="MenuListItem-info-container">
            <div className="MenuListItem-menu-name-container">
              <div className="title">{props.item.title}</div>
            </div>
            <div className="MenuListItem-addresse-container">
              <div>{props.item.cost}원</div>
            </div>
            
          </div>
          <div className="MenuListItem-btn-container">
          <input type='button' className='add-btn' onClick={menuDelete} value={"삭제"}/>
          </div>
        </div>
      </div>
    </>
  );
}

export default MenuListItem;