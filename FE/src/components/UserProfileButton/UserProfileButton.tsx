import { styled } from "styled-components";
import DropdownItem from "../DropdownPanel/DropdownItem";
import DropdownPanel from "../DropdownPanel/DropdownPanel";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

type Props = {
  src: string;
  size?: "large" | "small";
  onClick(): void;
};

export default function UserProfileButton({
  src,
  size = "large",
  onClick,
}: Props) {
  const navigate = useNavigate();
  const [openProfileDropdown, setOpenProfileDropdown] =
    useState<boolean>(false);

  const goProfileEdit = () => {
    navigate("/profile");
  };

  const showProfileDropdown = () => {
    setOpenProfileDropdown(true);
  };

  const hideProfileDropdown = () => {
    setOpenProfileDropdown(false);
  };

  const confirmLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    setOpenProfileDropdown(false);
    navigate("/");
  };

  return (
    <Container>
      <Button
        onClick={() => {
          openProfileDropdown ? hideProfileDropdown() : showProfileDropdown();
        }}
        $size={size}
      >
        <UserImg src={src} />
      </Button>
      {openProfileDropdown && (
        <DropdownPanel
          title={"프로필 메뉴"}
          top={"40px"}
          left={"-200px"}
          closeDropdown={hideProfileDropdown}
        >
          <DropdownItem
            itemName={"프로필 수정"}
            value={""}
            checkbox={false}
            onClick={goProfileEdit}
          />
          <DropdownItem
            itemName={"다크모드"}
            criteria={localStorage.getItem("theme")?.toString()}
            value={"dark"}
            onClick={onClick}
          />
          <DropdownItem
            itemName={"로그아웃"}
            value={""}
            checkbox={false}
            onClick={confirmLogout}
          />
        </DropdownPanel>
      )}
    </Container>
  );
}

const Container = styled.div`
  position: relative;
`;

const Button = styled.button<{ $size: string }>`
  width: ${({ $size }) => ($size === "small" ? "20px" : "32px")};
  height: ${({ $size }) => ($size === "small" ? "20px" : "32px")};
`;

const UserImg = styled.img`
  width: 100%;
  height: 100%;
  border-radius: ${({ theme }) => theme.radius.half};
  object-fit: cover;
`;
