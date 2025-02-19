import styled, { css } from 'styled-components';

export const Container = styled.div<{ index: number }>`
  display: flex;
  background-color: white;
  padding: 1rem;
  width: 380px;
  border-radius: 0.4rem;
  box-shadow: 4px 4px 10px ${({ theme }) => theme.colors.secondary};
  ${({ index }) => css`
    animation: fade-in-${index} ${500 + index * 50}ms;

    @keyframes fade-in-${index} {
      0% {
        transform: translateY(-10px);
        scale: 1.1;
        opacity: 0;
      }

      ${index * 5}% {
        transform: translateY(-10px);
        scale: 1.1;
        opacity: 0;
      }
    }
  `}
`;

export const LeftSection = styled.div`
  position: relative;
  width: 20%;
`;

export const RightSection = styled.div`
  position: relative;
  width: 80%;
`;

export const ProfileImageWrapper = styled.div`
  position: absolute;
  left: -6rem;
  top: 1.6rem;
  width: 9.5rem;
  height: 9.5rem;
  border-radius: 50%;
  overflow: hidden;
  box-shadow: 5px 5px 10px ${({ theme }) => theme.colors.secondary};
`;

export const ProfileImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

export const UserInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-bottom: 0.9rem;
  gap: 0.1rem;
`;

export const UserNameWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 0.4rem;
`;

export const UserCareer = styled.div`
  display: flex;
  align-items: center;
  gap: 0.2rem;
  margin-top: 0.4rem;
`;

export const UserName = styled.span``;

export const InventoryWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  height: 7rem;
`;

export const LeftButton = styled.button`
  border: none;
  background: none;
  font-size: 1.5rem;
`;
export const RightButton = styled.button`
  border: none;
  background: none;
  font-size: 1.5rem;
`;

export const LinkWrapper = styled.a``;

export const ProfileViewButton = styled.button`
  border: none;
  width: 100%;
  height: 1.8rem;
  margin-top: 0.2rem;
  border-radius: 0.25rem;
  background-color: ${({ theme }) => theme.colors.primary};
`;

export const InventoryListWrapper = styled.div`
  overflow: hidden;
  height: 102px;
`;

export const InventoryList = styled.div<{ positionX: number }>`
  display: flex;
  align-items: flex-start;
  gap: 2rem;
  width: 200px;
  transform: ${({ positionX }) => `translateX(${positionX}px)`};
  transition: linear 0.2s;
`;

export const InventoryItem = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 2px;
`;

export const ProductImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

export const ProductTitle = styled.p`
  font-size: 0.3rem;
`;

export const ProductImageWrapper = styled.div`
  width: 80px;
  height: 80px;
`;

export const FollowerCountWrapper = styled.div`
  font-size: 0.7rem;
`;

export const FollowingButtonWrapper = styled.div`
  position: absolute;
  right: 0;
  top: 0;
`;

export const FollowingButton = styled.button<{ followed: boolean }>`
  width: max-content;
  height: max-content;
  padding: 0.3rem 0.6rem;
  font-size: 0.8rem;
  border-radius: 0.25rem;
  box-shadow: 2px 2px 6px ${({ theme }) => theme.colors.secondary};
  transition: linear 0.3s;

  background-color: ${({ theme, followed }) =>
    followed ? theme.colors.secondary : theme.colors.primary};
`;
