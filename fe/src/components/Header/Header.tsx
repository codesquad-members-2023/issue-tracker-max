import ProfileImage from "../../assets/ProfileImage.png";
import { useTheme } from "@emotion/react";
import { ColorScheme } from "../../contexts/ThemeContext";
import { useNavigate } from "react-router-dom";
import { Icon } from "../common/Icon";

export function Header() {
  const color = useTheme() as ColorScheme;
  const navigate = useNavigate();

  const onClickLogo = () => {
    navigate("/label");
    window.location.reload();
  };
  return (
    <div
      css={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        padding: "27px 80px",
        width: "100%",
        height: "94px",
        boxSizing: "border-box",
      }}>
      <div
        onClick={onClickLogo}
        css={{
          cursor: "pointer",
          position: "relative",
          width: "199px",
          height: "40px",
        }}>
        <Icon type="logoTypeMedium" color={color.neutral.text.strong} />
      </div>
      <img
        src={ProfileImage}
        alt="profile"
        css={{
          width: "32px",
          height: "32px",
          borderRadius: "50%",
          objectFit: "cover",
        }}
      />
    </div>
  );
}
